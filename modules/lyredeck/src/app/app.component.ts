import {Component, ViewChild} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  @ViewChild('sidenav') sidenav;

  title = 'app';

  context: String = 'dashboard';

  route(context: String): void {
    this.context = context;
    this.sidenav.close()
  }
}
