import { IsEmail,IsString,MinLength,MaxLength } from "class-validator";
export class CreateUserDto {
    @IsEmail({},{message:"Email is not valid"})
    email: string;

    @IsString()
    @MinLength(6,{message:"The password has to have more than 6 characters"})
    @MaxLength(20,{message:"The password has to have more than 6 characters"})
    password: string;
    
    @IsString()
    phone: string;
    @IsString()
    username: string;
}
