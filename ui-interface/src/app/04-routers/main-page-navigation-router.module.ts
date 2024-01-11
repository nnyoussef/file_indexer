import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: 'directory/create',
    loadComponent: () => import('../00-pages/02-directory-create/directory-create.component').then(c => c.DirectoryCreateComponent)
  },
  {
    path: 'directory/overview',
    loadComponent: () => import('../00-pages/01-directory-overview/directory-overview.component').then(m => m.DirectoryOverviewComponent)
  },
  {
    path: '',
    loadComponent: () => import('../00-pages/01-directory-overview/directory-overview.component').then(m => m.DirectoryOverviewComponent)
  },
  {
    path: 'file/create',
    loadComponent: () => import('../00-pages/03-file-upload/file-upload.component').then(m => m.FileUploadComponent)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    onSameUrlNavigation: 'reload',
    bindToComponentInputs: true
  })],
  exports: [RouterModule]
})
export class MainPageNavigationRouterModule {
}
