import { Component, signal, OnInit, inject } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { KeycloakProfile } from 'keycloak-js';

import Keycloak from 'keycloak-js';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, CommonModule],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App implements OnInit {
  protected readonly title = signal('front-end');

  
    private readonly keycloak = inject(Keycloak);

  
  isLoggedIn = false;
  public profile?: KeycloakProfile;
  
  async ngOnInit(): Promise<void> {
    this.isLoggedIn = await this.keycloak.authenticated ?? false;
    if (this.isLoggedIn) {
      this.profile = await this.keycloak.loadUserProfile();
    }
   } 

  public login(): void {
    console.log('[App] Login clicked');
    this.keycloak.login();
  }

  public logout(): void {
    console.log('[App] Logout clicked');
    this.keycloak.logout();
  }
}