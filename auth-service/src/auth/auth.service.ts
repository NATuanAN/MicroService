import { Inject, Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Auth } from './schema/auth.entity';
import { LoginDTO } from './dto/login.dto';
import { RegisterDTO } from './dto/register.dto';
import { firstValueFrom } from 'rxjs/internal/firstValueFrom';
import * as bcrypt from 'bcrypt';
import { HttpException, HttpStatus } from '@nestjs/common';
import { ClientProxy ,Payload} from '@nestjs/microservices';
import { Cache } from 'cache-manager';
import { log } from 'console';

@Injectable()
export class AuthService {
  constructor(private jwtService: JwtService,
    @InjectRepository(Auth) private authRepo: Repository<Auth>,
    @Inject('USER_SERVICE') private readonly userClient: ClientProxy,
    @Inject('CACHE_MANAGER') private readonly redis: Cache) { }
  async register(dto: RegisterDTO) {
    console.log("This is in register of auth")
    const check_email = await this.authRepo.findOne({ where: { email: dto.email } });

    if (check_email) {
      throw new HttpException('The email already exists', HttpStatus.CONFLICT);
    }
    try {
      const payload = { email: dto.email, phone: dto.phone ,username:dto.username};
      const hashedPass = await bcrypt.hash(dto.password, 10);
      const userId = await firstValueFrom(this.userClient.send('create_user', payload), { defaultValue: null });
      if (!userId)
        throw new HttpException('Fail in creating a new user', HttpStatus.BAD_REQUEST);
      
      const user = await this.authRepo.save({ ...dto, password: hashedPass,userId: userId});
      const { password, ...responseUser } = user;
      
      console.log(responseUser.userId);
      return {
        message: 'Register successful',
        data: responseUser,
      };
      
    } catch (error) {
      throw new HttpException('Internal Server Error', HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  async login(dto: LoginDTO)
  {
    const user = await this.authRepo.findOne({ where: { email: dto.email } });
    
    if (!user)
      throw new HttpException("The email is not exist", HttpStatus.NOT_FOUND);
    
    try {
      console.log("there is in auth/login")
      const isMatch = await bcrypt.compare(dto.password, user.password);
      if (!isMatch)
      {
        console.log("Password is not correct")
        throw new HttpException("Password is not correct",HttpStatus.UNAUTHORIZED)
      } 

      const payload = { sub: user.userId ,role:user.role};
      const accessToken = this.jwtService.sign(payload);
      const refreshToken = await crypto.randomUUID();

      await this.redis.set(`refresh:${user.userId}:${refreshToken}`, refreshToken, 3600 * 7);

      console.log("Login successful");
      return { "message": "Login successful",accesstoken: accessToken,refreshtoken: refreshToken};
    }
    catch (e) {   
      if (e instanceof (HttpException))
        throw e;
      console.log("The error is ", e);
      throw new HttpException("Something were wrong in server", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
} 