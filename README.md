# GitHub-Api-pagination
A simple android app made with mvvm to see Github closed PRs.

Demo video : https://youtu.be/YyGJ7a2l2gM (video screenshot feature keeps crashing, thats why the poor video quality)

Supports: Pagination. Api failure during pagination. Many states handled using Pagination3 library.



---
Choice of architecture : MVVM using paging library.
![myimage-alt-tag](https://developer.android.com/topic/libraries/architecture/images/paging3-library-architecture.svg)

Supports pull to refresh as well.

Optimization:
Right now the data is kept in memory. We can persist it to a local DB for better performance when we have to deal with large amounts of data.
The user error messages are highly technical now. This should be much more user friendly.

