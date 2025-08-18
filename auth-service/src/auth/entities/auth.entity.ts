import { Entity,Column,PrimaryGeneratedColumn,CreateDateColumn,UpdateDateColumn, Unique } from "typeorm";
import { authRole } from "./role.enum";

@Entity('auths')
@Unique(["email"])
export class Auth { 
    @PrimaryGeneratedColumn('uuid')
    id: string;
    @Column({type:"varchar",length:255})
    email: string;

    @Column({type:"varchar",length:255})
    password: string;

    @Column({type:'uuid'})
    userId: string;
    
    @Column({
        type: 'enum',
        enum: authRole,
        default:authRole.GUEST
    })
    role: authRole;

    @CreateDateColumn()
    createdAt: Date;

    @UpdateDateColumn()
    updatedAt: Date; 
}
