import { PartialType } from '@nestjs/mapped-types';
import { RegisterDTO } from './register.dto';

export class UpdateDTO  extends PartialType(RegisterDTO){     }
