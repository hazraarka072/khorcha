import { TokenService } from './TokenService';

interface ApiClientOptions {
  baseURL: string;
}

export class ApiClient {
  private baseURL: string;

  constructor(options: ApiClientOptions) {
    this.baseURL = options.baseURL;
  }

  private async getHeaders(): Promise<Headers> {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const accessToken = await TokenService.getAccessToken();
    const idToken = await TokenService.getIdToken();
    if (idToken) {
      headers.append('Authorization', `Bearer ${idToken}`);
    }

    return headers;
  }

  public async get<T>(endpoint: string): Promise<T> {
    const headers = await this.getHeaders();
    const response = await fetch(`${this.baseURL}${endpoint}`, {
      method: 'GET',
      headers,
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    return response.json();
  }

  public async post<T>(endpoint: string, data: any): Promise<T> {
    const headers = await this.getHeaders();

    const response = await fetch(`${this.baseURL}${endpoint}`, {
      method: 'POST',
      headers,
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    return response.json();
  }
}