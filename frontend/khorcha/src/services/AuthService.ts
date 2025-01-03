import { UserManager, User, WebStorageStateStore } from 'oidc-client-ts';
import { authConfig } from '../config/auth.config';

class AuthService {
  private userManager: UserManager;

  constructor() {
    this.userManager = new UserManager({
      ...authConfig,
      userStore: new WebStorageStateStore({ store: window.localStorage }),
    });
  }

  public async login(): Promise<void> {
    try {
      await this.userManager.signinRedirect();
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  }

  public async logout(): Promise<void> {
    try {
      await this.userManager.signoutRedirect();
    } catch (error) {
      console.error('Logout error:', error);
      throw error;
    }
  }

  public async handleLoginCallback(): Promise<User | null> {
    try {
      const user = await this.userManager.signinRedirectCallback();
      return user;
    } catch (error) {
      console.error('Login callback error:', error);
      throw error;
    }
  }

  public async getUser(): Promise<User | null> {
    try {
      const user = await this.userManager.getUser();
      return user;
    } catch (error) {
      console.error('Get user error:', error);
      return null;
    }
  }

  public async isAuthenticated(): Promise<boolean> {
    try {
      const user = await this.userManager.getUser();
      return !!user && !user.expired;
    } catch (error) {
      return false;
    }
  }
}

export const authService = new AuthService();