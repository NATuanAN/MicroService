import { CanActivate, ExecutionContext, HttpException, HttpStatus, Injectable } from "@nestjs/common";

@Injectable()
export class RightId implements CanActivate{
    async canActivate(context: ExecutionContext) {
        const request = context.switchToHttp().getRequest();
        const user = request.user;
        const param = request.params;
        
        // console.log(user)
        if (user.userId!==param.id && user.role!=='admin')
            throw new HttpException('This user is not allowed', HttpStatus.UNAUTHORIZED);
        return true;
    }
}