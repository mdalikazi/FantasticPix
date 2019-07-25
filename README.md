# README #

#### Summary ####
* In this challenge I think I have appropriately met the UI requirements. I have used Google's MVVM architecture with `ViewModel` and `LiveData` for this challenge. The search feature has been created using the [SearchView](https://developer.android.com/reference/android/widget/SearchView). For displying the photos list I used a `RecyclerView` with a `GridLayoutManager`. For me the implementation of this structure was straight forward.
* In case of extra tasks I did not get time to implement pagiation and caching features, though I gave pagination a shot on a separate branch and I would finish it if I had more time. I will comment more on this below.

#### Notes on extra features ####
* Pagination
    - I would implement this using Google's [Paging library](https://developer.android.com/topic/libraries/architecture/paging.html). I tried to implement it because I have done this beore in a side project I did on my own in 2018, that fetched paged photos from 500px.com. I had built the [sample app](https://github.com/mdalikazi/codesample-FantasticPix) to learn how to implement pagination via Google's Paging library. In case of this challenge I faced trouble because I tried to look into pagination directly from network rather than adding a Room database. I read through some of the info on this and it seemed more complex than I would imagine, because the Paging library needs `DataSource.Factory<Int, Photo>` for it to work. I can easily get a datasource from Room, but creating a source manually seems a bit involved. More info [here](https://developer.android.com/topic/libraries/architecture/paging/data#custom-data-source).  
    I will be looking into this to learn how to do it directly from network by looking at the [Paging with Network sample](https://github.com/googlesamples/android-architecture-components/tree/master/PagingWithNetworkSample). So if I had more time I would finish pagination by adding a Room database to the project.
 * Caching
     - In case of caching, there are 2 types of data that can be saved: plain text data and images.
          - The text data can be easily saved to a Room database by converting the data classes to Entities and saving responses from API to database then loading from database in the AppRepository. Though, I am not sure if this would work for this challenge because the user may search unlimited queries and each query can result in different images. So I dont see a point in implemeting database in this challenge.
          - For images I would store them to the disk, but it makes sense to do this for a Download feature where a user can choose to download an image to their device. For general image caching, I would refer to [Glide's caching implementation](https://bumptech.github.io/glide/doc/caching.html)  

#### If I had more time ####
* With more time I would
    - Write unit and instrumented tests, fix any bugs that may result from tests.
    - Complete the pagination feature with Room database.

Ali Kazi   
[LinkedIn](linkedin.com/in/mdalikazi)  
