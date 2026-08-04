import { SignInResponse } from './response.interfaces';
import { MfaChallengeResponse } from './mfa-response-interface';

export type LoginResponse = SignInResponse | MfaChallengeResponse;

export function isMfaChallengeResponse(response: LoginResponse): response is MfaChallengeResponse {
  return (response as MfaChallengeResponse).challengeToken !== undefined;
}
