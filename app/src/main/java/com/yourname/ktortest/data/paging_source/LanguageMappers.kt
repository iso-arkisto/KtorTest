package com.yourname.ktortest.data.paging_source

import com.yourname.ktortest.data.local.LanguageEntity
import com.yourname.ktortest.domain.model.ProgrammingLanguage

fun ProgrammingLanguage.toEntity(): LanguageEntity {
    return LanguageEntity(
        id = id,
        image = image,
        shortName = shortName,
        creator = creator,
        inceptionYear = inceptionYear,
        name = name
    )
}