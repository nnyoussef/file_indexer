import {AppModule} from "./app/00-pages/app.module";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import 'zone.js';

platformBrowserDynamic().bootstrapModule(AppModule, {
  ngZoneEventCoalescing: true,
  ngZoneRunCoalescing: true
}).catch((err) => console.error(err))
