export interface Project1 {
    id: number;
    nom: string;
    status: any;
    timestamp: Date;
  }
  
  export interface Project {
    id: number;
    name?: string; 
    nom?: string;
    description: string;
    status: Status;
    santeGenerale: SanteGenerale;
    respectPlanning: Avancement;
    respectPerimetre: Avancement;
    respectBudget: RespectBudget;
    startDate: Date;
    endDate: Date;
    dateLivraison: Date;
    priority: number;
    type: string;
    budget: number;
    actualBudget: number;
    assignedUser?: number;
    tasks: Task[];
    users: User[];
  }
  
  
  export interface Task {
    id: number;
    title: string;
    description: string;
    status: Status;
    startDate: Date;
    endDate: Date;
    priority: number;
    users?: User[];
    comments?: Comment[];
    project?: Project;
    progress?: number;
  }
  
  export interface User {
    id: number;
    username: string;
    email: string;
    grade: string;
    role: Role;
    worken: boolean;
  }
  
  export interface Comment {
    id: number;
    date: Date;
    content: string;
    authorType: string;
    commentableType: string;
    author?: User;
    task?: Task;
  }
  
  export enum Status {
    EN_COURS = 'EN_COURS',
    ANNULE_ET_CLOTURE = 'ANNULE_ET_CLOTURE',
    LIVRE_ET_CLOTURE = 'LIVRE_ET_CLOTURE',
    LIVRE = 'LIVRE',
    NON_DEMARRE = 'NON_DEMARRE',
    EN_ATTENTE = 'EN_ATTENTE'
  }
  export enum Role {
    ADMIN = 'ADMIN',
    PROJECT_MANAGER = 'PROJECT_MANAGER',
    PERSONNEL_ADMINISTRATIF = 'PERSONNEL_ADMINISTRATIF',
    TEAM_LEADER = 'LTEAM_LEADER',
    DEVELOPER = 'DEVELOPER',
    TESTER = 'TESTER',
    DESIGNER = 'DESIGNER'
  }
  
  export interface ProjectStatusHistory {
    id: number;
    projectId: number;
    newStatus: string;
    changeDate: Date;
    projectName?: string;
  }
  export enum SanteGenerale {
    SeDerouleCommePrevu = 'Se_déroule_comme_prévu',
    ASubitDesChangements = 'A_subit_des_changements',
    EnDerive = 'En_dérive'
  }
  
  export enum Avancement {
    Respecte = 'Respecté',
    EnRetard = 'En_retard',
    PlusOuMoinsRespecte = 'Plus_ou_moins_respecté'
  }
  
  export enum RespectBudget {
    Respecte = 'Respecté',
    EnSurconsommation = 'En_surconsommation'
  }