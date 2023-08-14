export class AuthToken {
  constructor(
    public token: string, 
    public expiration: Date) {}
}