// SSR route metadata kept as plain data so the project does not depend on `@angular/ssr` types.
// This preserves intent (prerendering) while avoiding runtime type/import errors.
export const serverRoutes = [
  {
    path: '**',
    // Intent: prerender this route. Kept as string to avoid depending on `RenderMode` type.
    renderMode: 'Prerender'
  }
];
