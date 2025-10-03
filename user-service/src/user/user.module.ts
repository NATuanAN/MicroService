import { Module } from '@nestjs/common';
import { UserService } from './user.service';
import { UserController } from './user.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { User } from './schema/user.entity';
import { JwtStrategy } from './jwt/jwt-strategy';
import { JwtModule } from '@nestjs/jwt';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { CacheModule } from '@nestjs/cache-manager';
import * as redisStore from 'cache-manager-redis-store'
import { ClientsModule, Transport } from '@nestjs/microservices';

@Module({
  imports:[
    TypeOrmModule.forFeature([User]),
    JwtModule.registerAsync({
      imports: [ConfigModule],
      inject: [ConfigService],
      useFactory: (config: ConfigService) => ({
        secret: config.getOrThrow('JWT_SECRET'),
        signOptions: {expiresIn:'1h'},
        
      })
    }),
    CacheModule.register({
      store: redisStore,
      host: 'localhost',
      port: 6673,
      ttl: 60,
      max: 100,
    }),
    ClientsModule.register([
      {
        name: "PRODUCT-SERVICE",
        transport: Transport.RMQ,
        options: {
          urls: ["amqp://localhost:5672"],
          queue: "product_queue",
          queueOptions:{durable:true},
        }
      }
    ]),
  ],
  controllers: [UserController],
  providers: [UserService,JwtStrategy],
})
export class UserModule {}
 