package com.wednesday.template.domain.lastfm

import com.wednesday.template.domain.base.BaseSuspendUseCase

interface SearchAlbumsUseCase : BaseSuspendUseCase<String, List<Album>>