import { Column, Entity, PrimaryGeneratedColumn, Unique, } from 'typeorm'

@Entity('users')
@Unique(['email','phone'])
export class User{
    @PrimaryGeneratedColumn('uuid')
    userId: string;
    
    @Column({ type: 'varchar', length: 255 })
    username: string;

    @Column({ type: 'varchar', length: 255 })
    phone: string;  

    @Column({ type: 'varchar', length: 255 })
    email: string;
}