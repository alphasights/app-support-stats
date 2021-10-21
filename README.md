## Intercom Integration

Idea by: Elina Yu

### What problem is this project trying to solve and for who?

Solve the issue of manually pulling Intercom tagging data for AppSupport (and by extension, Product and Engineering)

What impact could this project have on the company (business, technical and/or recruiting)?
Makes user issue data (through Intercom tagging) more easily accessible - eventually, this will allow Product/Engineering to assess rollout success, features/points where users are facing issues, without forcing AppSupport to pull this information manually and inefficiently in order to share it.

### What is the purpose / objective of the project?

Currently, AppSupport is pulling intercom tagging statistics manually for the support update given to product and engineering. As the team revamps this process, creating tags that more accurately and precisely identify user pain points, recurring issues, etc., the team needs a better way to pull this data, especially as we start rolling it out to more and more engineering pods. Intercom seems to have a solution for this (https://developers.intercom.com/building-apps/docs/rest-apis) - ideally, Engineering could somehow pull this tagging data into Periscope (or Tableau, etc.) so that ASE don't need to manually export the info. Beyond that, we don't have too many specifications on how this could be achieved. Charlotte also mentioned that would be helpful for Product if they could also access this data - for example, an APM could query just to see the stats for a specific feature they own over a specific time period.
