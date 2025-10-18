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
        host: cf.get('MYSQL_HOST'),
        port: parseInt(cf.getOrThrow('MYSQL_PORT')),
        username: cf.get('MYSQL_USER'),
        password: cf.get('MYSQL_PASSWORD'),
        database: cf.get('MYSQL_DB'),
        entities: [Auth],
        // synchronize: true,
      })
    }),

    AuthModule,
  ],
})
export class AppModule {}
