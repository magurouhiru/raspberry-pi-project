import { Routes } from '@angular/router';

import { HelloComponent } from './component/hello/hello.component';
import { HomeComponent } from './component/home/home.component';
import { CustomData } from './routerData';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    data: { title: '' } satisfies CustomData,
  },
  {
    path: 'hello',
    component: HelloComponent,
    data: { title: 'Hello' } satisfies CustomData,
  },
];
