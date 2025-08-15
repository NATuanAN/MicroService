import { CanActivate, ExecutionContext, Inject } from "@nestjs/common";
import { Reflector } from "@nestjs/core";
import { Cache } from "cache-manager";
import { Observable } from "rxjs";

export class RoleGuard implements CanActivate{
    constructor(
        private reflector: Reflector,
        @Inject('CACHE_MANAGER') private cache:Cache
    ) { }

    async canActivate(context: ExecutionContext):Promise<boolean> {
        
    }
}