export interface AuthUserDTO {
    username?:string | any
    email?:string | any
    password?:string | any
}

export interface TokenResponse {
    token:string
    refreshToken:string
}