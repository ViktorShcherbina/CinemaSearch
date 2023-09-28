package store.devshcherbinavv.remote_module

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [RemoteModule::class]
)
interface RemoteComponent : RemoteProvider