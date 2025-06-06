import { UserResponse } from "../user/UserResponse";

export interface AuthenticationResponse {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
  UserResponse: UserResponse;
}
