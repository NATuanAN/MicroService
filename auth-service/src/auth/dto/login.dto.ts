import { IsEmail, IsString, MinLength ,MaxLength} from 'class-validator';
export class LoginDTO  {
    @IsEmail({},{message:"Email is not valid"})
    email: string;

    @IsString()
    password: string;
}
