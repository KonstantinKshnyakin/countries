package quru.qa.petproject.countries.countries.service.impl;

import com.google.common.collect.Lists;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quru.qa.petproject.countries.countries.dto.Country;
import quru.qa.petproject.countries.countries.mapper.CountryMapper;
import quru.qa.petproject.countries.countries.service.CountryService;
import quru.qa.petproject.countries.countries.util.ThrowingRunnable;
import quru.qa.petproject.countries.grpc.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CountryGrpcServiceImpl extends CountriesServiceGrpc.CountriesServiceImplBase {

    private final CountryService countryService;
    private final CountryMapper mapper;
    @Value("${app.grpc-countries-batch-size:5}")
    private Integer batchSize;

    @Override
    public void add(AddCountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        processWithCompleted(responseObserver, () -> {
            Country country = countryService.add(mapper.toCountry(request));
            responseObserver.onNext(mapper.toProtoResponse(country));
        });
    }

    @Override
    public void getAll(Empty request, StreamObserver<CountriesResponse> responseObserver) {
        processWithCompleted(responseObserver, () -> {
            List<Country> countries = countryService.getAll();
            Lists.partition(countries, batchSize).stream()
                .map(mapper::toProtoResponse)
                .forEach(responseObserver::onNext);
        });
    }

    @Override
    public StreamObserver<CountryRequest> updateName(StreamObserver<AddedCountries> responseObserver) {
        return new StreamObserver<>() {
            AtomicInteger amount = new AtomicInteger();

            @Override
            public void onNext(CountryRequest country) {
                process(responseObserver, () -> {
                    countryService.updateName(mapper.toCountry(country));
                    amount.incrementAndGet();
                });
            }

            @Override
            public void onError(Throwable t) {
                process(responseObserver, () -> {
                    throw new RuntimeException(t);
                });
            }

            @Override
            public void onCompleted() {
                processWithCompleted(responseObserver, () -> {
                    AddedCountries summary = AddedCountries.newBuilder()
                        .setAmount(amount.get())
                        .build();
                    responseObserver.onNext(summary);
                });
            }
        };
    }

    private <T> void processWithCompleted(StreamObserver<T> responseObserver, ThrowingRunnable runnable) {
        process(responseObserver, () -> {
            runnable.run();
            responseObserver.onCompleted();
        });
    }

    private <T> void process(StreamObserver<T> responseObserver, ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            responseObserver.onError(
                Status.NOT_FOUND
                    .withDescription("Unexpected error: " + throwable.getMessage())
                    .withCause(throwable.getCause())
                    .asRuntimeException()
            );
        }
    }
}
