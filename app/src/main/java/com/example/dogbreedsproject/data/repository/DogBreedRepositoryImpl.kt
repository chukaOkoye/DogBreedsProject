package com.example.dogbreedsproject.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.dogbreedsproject.data.api.ApiService
import com.example.dogbreedsproject.data.db.DogBreedImagesDao
import com.example.dogbreedsproject.data.db.DogBreedsListDao
import com.example.dogbreedsproject.data.db.DogBreedsListEntity
import com.example.dogbreedsproject.data.db.toDogBreedImagesEntity
import com.example.dogbreedsproject.data.db.toDogBreedsEntity
import com.example.dogbreedsproject.data.db.toDomain
import com.example.dogbreedsproject.data.model.DogBreedImageListDTO
import com.example.dogbreedsproject.data.model.DogBreedsListDTO
import com.example.dogbreedsproject.domain.repository.DogBreedRepository
import com.example.dogbreedsproject.domain.model.DogBreedImageList
import com.example.dogbreedsproject.domain.model.DogBreedSearchResult
import com.example.dogbreedsproject.domain.model.DogBreedsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import kotlin.collections.emptyList
import kotlin.collections.emptyMap

class DogBreedRepositoryImpl @Inject constructor(private val apiService: ApiService,
                                                 private val dogBreedsListDao: DogBreedsListDao,
                                                 private val dogBreedImagesDao: DogBreedImagesDao)
    : DogBreedRepository {

    override suspend fun getDogBreedList(): Result<DogBreedsList>{
        return try {
            val response = apiService.fetchAllBreedsList()
            val cache = response.toDogBreedsEntity()

            dogBreedsListDao.insertDogBreeds(cache)

            if(response.message.isEmpty()){
                return Result.failure(IOException("Data unavailable"))
            }
            Result.success(response.toBreedsListDomain())

        } catch (e: IOException){
            val cachedEntities = dogBreedsListDao.getAllDogBreeds()

            if(cachedEntities.isNotEmpty()){
                val entity = cachedEntities.first()
                val domainModel = DogBreedsList(
                    message = entity.message,
                    status = "success"
                )
                return Result.success(domainModel)
            }
            Result.failure(e)
        }
    }

    override suspend fun getDogBreedRandomImage(breedName: String): Result<DogBreedImageList> {
        return try {
            val response = apiService.fetchRandomBreedImages(breedName)
            val cache = response.toDogBreedImagesEntity()

            dogBreedImagesDao.insertDogBreedImages(cache)

            if(response.message.isEmpty()){
                return Result.failure(Exception("Data breed not available"))
            }

            Result.success(response.toImageListDomain())
        } catch(e: Exception){
            val dogBreedImageEntities = dogBreedImagesDao.getImagesFromBreed(breedName)

            if(dogBreedImageEntities.isNotEmpty()){
                val entity = dogBreedImageEntities.first()
                val imageDomainModel = DogBreedImageList(
                    message = entity.message
                )
                return Result.success(imageDomainModel)
            }

            Result.failure(e)
        }
    }

    override fun getDogBreedSearchQuery(input: String): Flow<DogBreedSearchResult>{

        return dogBreedsListDao.getSpecificDogBreed("%$input%").map { entities ->
            val entity = entities.firstOrNull()?.message ?: emptyMap()
            val filteredMap = entity.filter { (breedName,_) ->
                breedName.contains(input)
            }
            DogBreedSearchResult(filteredMap)
        }
    }


}

fun DogBreedImageListDTO.toImageListDomain(): DogBreedImageList{
    return DogBreedImageList(
        message = message
    )
}


fun DogBreedsListDTO.toBreedsListDomain(): DogBreedsList{
    return DogBreedsList(
        message = message,
        status = status
    )
}

//  return dogBreedsListDao.getSpecificDogBreed("%$input").map { entities ->
//           val entity = entities.firstOrNull()?.message ?: emptyMap()
//
//           val filteredMap = entity.filter{ (breedName,_) ->
//               breedName.contains(input, true)
//           }
//           DogBreedSearchResult(message = filteredMap)
//       }


//    override fun getDogBreedSearchQuery(input: String): Flow<DogBreedSearchResult> {
//        // 1. Get the Flow from the DAO
//        return dogBreedsListDao.getSpecificDogBreed("%$input%").map { entities ->
//            // 2. Get the one row that contains the full JSON map
//            val entity = entities.firstOrNull()
//            val fullMap = entity?.message ?: emptyMap()
//
//            // 3. Filter the Map keys in Kotlin based on the search input
//            val filteredMap = fullMap.filter { (breedName, _) ->
//                breedName.contains(input, ignoreCase = true)
//            }
//
//            // 4. Wrap the filtered map in your result class
//            DogBreedSearchResult(message = filteredMap)
//        }
//    }




//fun DogBreedsListEntity.toDomain(): DogBreedSearchResult{
//    return DogBreedSearchResult(
//        message = (
//            DogBreedsList(
//                this.message,
//                this.status,
//            )
//        )
//    )
//}
//
