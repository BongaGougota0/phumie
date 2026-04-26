package za.phumie.shared.template;

import reactor.core.publisher.Mono;

public abstract class CacheTemplate<K, E> {
    public Mono<E> get(K id) {
        return getFromCache(id)
                .switchIfEmpty(getFromSource(id)
                        .flatMap(e -> updateCache(id, e)));
    }

    public Mono<E> update(K id, E object) {
        return updateSource(id, object)
                .flatMap(e -> deleteFromCache(id).thenReturn(object));
    }

    public Mono<Void> delete(K id) {
        return deleteFromSource(id).then(deleteFromCache(id));
    }

    abstract protected Mono<E> getFromSource(K key);
    abstract protected Mono<E> getFromCache(K key);
    abstract protected Mono<E> updateSource(K key, E entity);
    abstract protected Mono<E> updateCache(K key, E entity);
    abstract protected Mono<Void> deleteFromSource(K key);
    abstract protected Mono<Void> deleteFromCache(K key);
}