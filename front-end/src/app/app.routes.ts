
import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Person } from './person/person';
import { Product } from './product/product';
import { canActivateAuthRole } from './guards/auth.guards';
import { ForbiddenComponent } from './ForbiddenComponent';

export const routes: Routes = [
    { path: '', component: Home },
    { path: 'products', component: Product , canActivate: [canActivateAuthRole],data: { role: 'ADMIN' } },
    { path: 'forbidden', component: ForbiddenComponent },
    { path: 'persons', component: Person }
];
