import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { ToolbarModule } from 'primeng/toolbar';
import { AvatarModule } from 'primeng/avatar';

@Component({
  selector: 'app-app-layout',
  standalone: true,
  imports: [RouterOutlet, 
    ToolbarModule,
    AvatarModule,
    RouterModule ],
  templateUrl: './app-layout.component.html',
  styleUrl: './app-layout.component.css'
})
export class AppLayoutComponent {

}
