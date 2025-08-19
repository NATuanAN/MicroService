import { Controller, Get, HttpException, HttpStatus, Param, Req, UseGuards } from '@nestjs/common';
import { MessagePattern, Payload } from '@nestjs/microservices';
import { UserService } from './user.service';
import { CreateUserDto } from './dto/create-user.dto';
import { IsOwner } from './decorator/isowner';
@Controller()
export class UserController {
  constructor(private readonly userService: UserService) {}

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
    return await this.userService.createUser(payload);
  }
}
