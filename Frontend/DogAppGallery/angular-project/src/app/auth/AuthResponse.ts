export interface SignInResponse {
  token: string;
  email: string;
  roles: string[];
}

export interface MfaChallengeResponse {
  challengeToken: string;
}

export type LoginResponse = SignInResponse | MfaChallengeResponse;

export function isMfaChallengeResponse(response: LoginResponse): response is MfaChallengeResponse {
  return (response as MfaChallengeResponse).challengeToken !== undefined;
}
