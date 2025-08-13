import { Module } from '@nestjs/common';
import { AuthService } from './auth.service';
import { AuthController } from './auth.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Auth } from './entities/auth.entity';
import { JwtModule } from '@nestjs/jwt';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { ClientsModule,Transport } from '@nestjs/microservices';
import { CacheModule } from '@nestjs/cache-manager';
import * as redisStore from 'cache-manager-redis-store';

@Module({
  imports: [
    TypeOrmModule.forFeature([Auth]),
    JwtModule.registerAsync({
      imports:[ConfigModule],
      inject: [ConfigService],
      useFactory: (config: ConfigService) =>
      ({
        secret: config.get<string>('JWT_SECRET'),
        signOptions: { expiresIn: '1d' },
        ignoreExpiration:false,
      })
    }),
     ClientsModule.register([
      {
        name: 'USER_SERVICE',
        transport: Transport.RMQ,
        options: {
          urls: ['amqp://localhost:5672'],
          queue: 'user_queue',
          queueOptions: { durable: false }
        }
      }
    ]),

    CacheModule.register({
      store:redisStore,
      host: 'localhost',
      port: 6379,
      ttl: 60,
      max: 100,
    }),
  ],
  controllers: [AuthController],
  providers: [AuthService],

})
export class AuthModule {}
