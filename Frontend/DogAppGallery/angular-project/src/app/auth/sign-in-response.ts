import { SignInResponse, MfaChallengeResponse } from './response.interfaces';

export type LoginResponse = SignInResponse | MfaChallengeResponse;

export function isMfaChallengeResponse(response: LoginResponse): response is MfaChallengeResponse {
  return (response as MfaChallengeResponse).challengeToken !== undefined;
}
