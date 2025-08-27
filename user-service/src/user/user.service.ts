import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm'; 
import {Repository} from 'typeorm'
import { User } from './schema/user.entity';

import { HttpException,HttpStatus } from '@nestjs/common';
import { CreateUserDto } from './dto/create-user.dto';
@Injectable()
export class UserService {
  constructor(@InjectRepository(User) private userRepo: Repository<User>){}
async getUserbyEmail(email:string)
  {
    try {
        const temp = await this.userRepo.findOne({ where: { email} });
        if (!temp)
          throw new HttpException("The user email is not found", HttpStatus.NOT_FOUND);
        return temp;
      }
      catch (e) {
      if (e instanceof HttpException) {
        throw e;
      }
      throw new HttpException("The error in server", HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  async getUserbyId(userId:string)
  {
    try {
      const temp = await this.userRepo.findOne({ where: { userId} });
      if (!temp)
      {
        throw new HttpException("The user id is not found", HttpStatus.NOT_FOUND);
      }
        return temp;
      }
      catch (e) {
        if (e instanceof HttpException) {
          throw e;
        }
        throw new HttpException("The error in server", HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  async createUser(dto:CreateUserDto){
    const newUser = await this.userRepo.save(dto);
    return newUser.userId;
  }
}
