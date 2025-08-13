import { IsEmail, IsString, MinLength ,MaxLength} from 'class-validator';
export class RegisterDTO { 
    @IsEmail({},{message:"Email is not valid"})
    email: string;

    @IsString()
    @MinLength(6,{message:"The password has to have more than 6 characters"})
    @MaxLength(20,{message:"The password has to have more than 6 characters"})
    password: string;
    
    @IsString()
    username: string;

    @IsString()
    phone: string;
}
