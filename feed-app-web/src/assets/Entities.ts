export type Poll = {
    id: number;
    question: string;
    noCount: number;
    yesCount: number;
    startTime: Date;
    endTime: Date;
    status: number;
    accessCode: string;
    public: boolean;
};

export type FAUser = {
    email: String;
    name: String;
    userId: number;
};

