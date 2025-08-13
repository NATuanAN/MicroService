import { Controller, Get, Post, Body, Patch, Param, Delete, UseGuards,Headers,Req,Ip,UnauthorizedException, HttpException, HttpStatus } from '@nestjs/common';
import { AuthService } from './auth.service';
import { RegisterDTO } from './dto/register.dto';
import { LoginDTO } from './dto/login.dto';


@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}
  @Post('register')
  async register(@Body() dto: RegisterDTO)
  {
    return await this.authService.register(dto);
  }
  @Post('login')
  async login(@Body() dto: LoginDTO, @Req() req: Request)
  {
    return await this.authService.login(dto); 
  }

//   @UseGuards(JwtGuard)
//   @Get('user/:id')
//   async getUserbyId(@Param('id') id: string, @Req() req: any) {
//     if (id !== req.user.id)
//       throw new HttpException("The user doest have authorized to access this resource",HttpStatus.UNAUTHORIZED)
//     return await this.authService.getUserbyId(id);
// }
}
