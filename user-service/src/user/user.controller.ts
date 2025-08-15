import { Controller, Get, HttpException, HttpStatus, Param, Req, UseGuards } from '@nestjs/common';
import { MessagePattern, Payload } from '@nestjs/microservices';
import { UserService } from './user.service';
import { JwtGuard } from './jwt/jwt-guard'
import { CreateUserDto } from './dto/create-user.dto';

@Controller()
export class UserController {
  constructor(private readonly userService: UserService) {}

  @Get('user/:id')
  @UseGuards(JwtGuard)
  async getuserbyid(@Param('id') id :string,@Req() req :any)
  {
    const user = await this.userService.getUserbyId(id);
    if (req.user.userId!== user.userId)
      throw new HttpException('Unauthorized', HttpStatus.UNAUTHORIZED);
    return req.user;
  }
  @MessagePattern('create_user')
  async createUserreturnId(payload:CreateUserDto)
  {
    return await this.userService.createUser(payload);
  }
}
