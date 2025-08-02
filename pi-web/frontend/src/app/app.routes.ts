import { Routes } from '@angular/router';

import { DeviceComponent } from './component/device/device.component';
import { HelloComponent } from './component/hello/hello.component';
import { HomeComponent } from './component/home/home.component';
import { TablesComponent } from './component/tables/tables.component';
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
  {
    path: 'tables',
    component: TablesComponent,
    data: { title: 'Tables' } satisfies CustomData,
  },
  {
    path: 'device',
    component: DeviceComponent,
    data: { title: 'Device' } satisfies CustomData,
  },
];
