import { Injectable, UnauthorizedException ,Logger} from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { ExtractJwt, Strategy } from "passport-jwt";
import { ConfigService } from "@nestjs/config";
import { UserService } from "../user/user.service";
@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy)
{
  constructor(private userService:UserService, private cf: ConfigService) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      secretOrKey: cf.getOrThrow<string>('JWT_SECRET'),
      ignoreExpiration:false,
    })
  }

  async validate(payload: any) {
    const user = await this.userService.getUserbyeEmail(payload.email);
    if(user)
      console.log('user.id:', user.userId);
    return user;
  }
}