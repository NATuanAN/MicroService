    import { applyDecorators,UseGuards } from "@nestjs/common";
    import { RightId } from '../guard/is_owner';
    import { JwtGuard } from "../jwt/jwt-guard";
    export function IsOwner()
    {
        return applyDecorators(UseGuards(JwtGuard,RightId));
    }