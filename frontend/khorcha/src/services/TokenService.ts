import { User } from 'oidc-client-ts';
import { authService } from './AuthService';

export class TokenService {
  public static async getAccessToken(): Promise<string | null> {
    const user: User | null = await authService.getUser();
    return user?.access_token || null;
  }

  public static async getIdToken(): Promise<string | null> {
    const user: User | null = await authService.getUser();
    return user?.id_token || null;
  }

  public static async isTokenValid(): Promise<boolean> {
    const user = await authService.getUser();
    if (!user) return false;
    return !user.expired;
  }
}