import { Module  } from '@nestjs/common';
import { AuthModule } from './auth/auth.module';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { Auth } from './auth/schema/auth.entity';
import { TypeOrmModule } from '@nestjs/typeorm';


@Module({
  imports: [
    ConfigModule.forRoot({ isGlobal: true, }),
    TypeOrmModule.forRootAsync({
      imports: [ConfigModule],
      inject: [ConfigService],
      useFactory: (cf: ConfigService) =>
      ({
        type: 'mysql',
        host: cf.get('DB_HOST'),
        port: parseInt(cf.getOrThrow('DB_PORT')),
        username: cf.get('DB_USERNAME'),
        password: cf.get('DB_PASSWORD'),
        database: cf.get('DB_NAME'),
        entities: [Auth],
        // synchronize: true,
      })
    }),

    AuthModule,
  ],
})
export class AppModule {}
