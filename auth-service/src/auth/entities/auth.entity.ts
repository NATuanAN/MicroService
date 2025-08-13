import { Entity,Column,PrimaryGeneratedColumn,CreateDateColumn,UpdateDateColumn, Unique } from "typeorm";


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
    
    @CreateDateColumn()
    createdAt: Date;

    @UpdateDateColumn()
    updatedAt: Date; 
}
