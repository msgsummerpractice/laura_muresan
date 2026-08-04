export interface SignInResponse {
  token: string;
  email: string;
  roles: string[];
}

export interface MfaChallengeResponse {
  challengeToken: string;
}
