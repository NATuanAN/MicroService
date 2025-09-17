import { Injectable, UnauthorizedException ,Logger} from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { ExtractJwt, Strategy } from "passport-jwt";
import { ConfigService } from "@nestjs/config";
import { UserService } from "../user.service";
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
    const user = await this.userService.getUserbyId(payload.sub);

    if(user)
      console.log('(In jwt) userId:', user.userId,' with role is ', payload.role);
    return { ...user, role: payload.role };
  }
}