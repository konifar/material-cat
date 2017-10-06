package com.konifar.materialcat.domain.di;

import com.konifar.materialcat.domain.repository.CatImageFlickrRepository;
import com.konifar.materialcat.domain.usecase.GetCatImageUseCase;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UseCaseModule_ProvideGetCatImageUseCaseFactory
    implements Factory<GetCatImageUseCase> {
  private final UseCaseModule module;

  private final Provider<CatImageFlickrRepository> arg0Provider;

  public UseCaseModule_ProvideGetCatImageUseCaseFactory(
      UseCaseModule module, Provider<CatImageFlickrRepository> arg0Provider) {
    assert module != null;
    this.module = module;
    assert arg0Provider != null;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public GetCatImageUseCase get() {
    return Preconditions.checkNotNull(
        module.provideGetCatImageUseCase(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<GetCatImageUseCase> create(
      UseCaseModule module, Provider<CatImageFlickrRepository> arg0Provider) {
    return new UseCaseModule_ProvideGetCatImageUseCaseFactory(module, arg0Provider);
  }
}
