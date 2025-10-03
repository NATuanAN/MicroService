import { Controller, Get, HttpException, HttpStatus, Inject, Param, Req, UseGuards } from '@nestjs/common';
import { MessagePattern, Payload , ClientProxy} from '@nestjs/microservices';
import { UserService } from './user.service';
import { CreateUserDto } from './dto/create-user.dto';
import { IsOwner } from './decorator/isowner';
import { firstValueFrom } from 'rxjs';
@Controller("user/")
export class UserController {
  constructor(private readonly userService: UserService,
    @Inject("PRODUCT-SERVICE") private readonly rab: ClientProxy
  ) {}

  @Get('user/:id')
  @IsOwner()
  async getuserbyid(@Param('id') id :string)
  {
    const user = await this.userService.getUserbyId(id);
    return user;
  }
  
  @MessagePattern('create_user')
  async createUserreturnId(payload:CreateUserDto)
  {
    console.log("there is in user")
    return await this.userService.createUser(payload);
  }
  @MessagePattern('test_user')
  async test_user(@Payload ("mess") payload: any)
  {
    console.log("there is in user");
    console.log(payload);
    return `Processed message: ${payload.mess || 'unknown'}`;
  }

  @Get("test")
  async test() {
    console.log("the mess is sent")
    const receivedmess = await firstValueFrom(this.rab.send('test_key', { "mess": "hello" }), { defaultValue: null });
    console.log(receivedmess)
  }
}
