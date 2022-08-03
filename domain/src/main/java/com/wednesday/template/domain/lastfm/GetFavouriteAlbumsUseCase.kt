package com.wednesday.template.domain.lastfm

import com.wednesday.template.domain.base.BaseSuspendUseCase

interface GetFavouriteAlbumsUseCase : BaseSuspendUseCase<Unit, List<Album>>