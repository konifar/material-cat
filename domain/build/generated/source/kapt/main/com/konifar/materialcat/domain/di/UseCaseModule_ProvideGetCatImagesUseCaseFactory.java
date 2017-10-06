package com.konifar.materialcat.domain.di;

import com.konifar.materialcat.domain.repository.CatImageFlickrRepository;
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UseCaseModule_ProvideGetCatImagesUseCaseFactory
    implements Factory<GetCatImagesUseCase> {
  private final UseCaseModule module;

  private final Provider<CatImageFlickrRepository> arg0Provider;

  public UseCaseModule_ProvideGetCatImagesUseCaseFactory(
      UseCaseModule module, Provider<CatImageFlickrRepository> arg0Provider) {
    assert module != null;
    this.module = module;
    assert arg0Provider != null;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public GetCatImagesUseCase get() {
    return Preconditions.checkNotNull(
        module.provideGetCatImagesUseCase(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<GetCatImagesUseCase> create(
      UseCaseModule module, Provider<CatImageFlickrRepository> arg0Provider) {
    return new UseCaseModule_ProvideGetCatImagesUseCaseFactory(module, arg0Provider);
  }
}
