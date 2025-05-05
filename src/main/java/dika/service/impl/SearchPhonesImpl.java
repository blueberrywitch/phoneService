package dika.service.impl;

import dika.enums.BatteryCapacity;
import dika.enums.ScreenDiagonal;
import dika.model.Characteristics;
import dika.model.Phone;
import dika.service.SearchPhones;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class SearchPhonesImpl implements SearchPhones {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Phone> searchAndSortPhones(
            List<String> brands,
            List<String> processor,
            List<String> screenDiagonal,
            List<String> internalStorage,
            List<String> batteryCapacity,
            Double priceMin,
            Double priceMax,
            String model,
            String sortDirection
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Phone> cq = cb.createQuery(Phone.class);
        Root<Phone> root = cq.from(Phone.class);
        Join<Phone, Characteristics> chr = root.join("characteristics", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        brandPredicate(predicates, brands, cb, root);

        processorPredicate(predicates, processor, cb, chr);

        screenDiagPredicate(predicates, screenDiagonal, cb, chr);

        internalStoragePredicate(predicates, internalStorage, cb, chr);

        batteryCapacityPredicate(predicates, batteryCapacity, cb, chr);

        pricePredicate(predicates, priceMin, priceMax, cb, root);

        modelPredicate(predicates, model, cb, root);

        if ("price_asc".equals(sortDirection)) {
            cq.orderBy(cb.asc(root.get("price")));
        } else if ("price_desc".equals(sortDirection)) {
            cq.orderBy(cb.desc(root.get("price")));
        }

        cq.select(root)
                .distinct(true);

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(cq)
                .getResultList();
    }

    private void addPredicateIfValid(List<Predicate> predicates, CriteriaBuilder criteriaBuilder,
                                     Root<Phone> root, String fieldName, String value) {
        if (StringUtils.hasText(value)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%"));
        }
    }

    private void brandPredicate(List<Predicate> predicates, List<String> brands,
                                CriteriaBuilder cb, Root<Phone> root) {
        if (brands != null && !brands.isEmpty()) {
            Predicate[] preds = brands.stream()
                    .map(b ->
                            cb.equal(
                                    cb.lower(root.get("brand").as(String.class)),
                                    b.toLowerCase()
                            )
                    )
                    .toArray(Predicate[]::new);
            predicates.add(cb.or(preds));
        }
    }

    private void processorPredicate(List<Predicate> predicates, List<String> processor,
                                    CriteriaBuilder cb, Join<Phone, Characteristics> chr) {
        if (processor != null && !processor.isEmpty()) {
            Predicate[] preds = processor.stream()
                    .map(b ->
                            cb.like(
                                    cb.lower(chr.get("processor").as(String.class)),
                                    "%" + b.toLowerCase() + "%"
                            ))
                    .toArray(Predicate[]::new);
            predicates.add(cb.or(preds));
        }
    }

    private void screenDiagPredicate(List<Predicate> predicates, List<String> screenDiag,
                                     CriteriaBuilder cb, Join<Phone, Characteristics> chr) {
        if (screenDiag != null && !screenDiag.isEmpty()) {
            Expression<Double> diagExpr = chr.get("screenDiagonal").as(Double.class);
            List<Predicate> orPreds = new ArrayList<>();
            for (String key : screenDiag) {
                ScreenDiagonal sd = ScreenDiagonal.valueOf(key);
                double min = sd.getDisplayValueMin();
                double max = sd.getDisplayValueMax();
                orPreds.add(cb.between(diagExpr, min, max));
            }
            predicates.add(cb.or(orPreds.toArray(new Predicate[0])));
        }
    }

    private void internalStoragePredicate(List<Predicate> predicates, List<String> internalStorage,
                                          CriteriaBuilder cb, Join<Phone, Characteristics> chr) {
        if (internalStorage != null && !internalStorage.isEmpty()) {
            log.info("Internal Storage: {}", internalStorage);
            Predicate[] preds = internalStorage.stream()
                    .map(b ->
                            cb.equal(
                                    cb.lower(chr.get("internalStorage").as(String.class)), b.toLowerCase()
                            )
                    )
                    .toArray(Predicate[]::new);
            predicates.add(cb.or(preds));
        }
    }


    private void batteryCapacityPredicate(List<Predicate> predicates, List<String> batteryCapacity,
                                          CriteriaBuilder cb, Join<Phone, Characteristics> chr) {
        if (batteryCapacity != null && !batteryCapacity.isEmpty()) {
            Expression<Double> battaryExpr = chr.get("batteryCapacity").as(Double.class);
            List<Predicate> orPreds = new ArrayList<>();
            for (String key : batteryCapacity) {
                BatteryCapacity sd = BatteryCapacity.valueOf(key);
                double min = sd.getCapacityMin();
                double max = sd.getCapacityMax();
                orPreds.add(cb.between(battaryExpr, min, max));
            }
            predicates.add(cb.or(orPreds.toArray(new Predicate[0])));
        }
    }

    private void pricePredicate(List<Predicate> predicates,  Double priceMin,  Double priceMax,
            CriteriaBuilder cb, Root<Phone> root
    ) {
        if (priceMin == null && priceMax == null) {
            return;
        }
        Path<Double> pricePath = root.get("price");

        if (priceMin != null && priceMax != null) {
            predicates.add(cb.between(pricePath, priceMin, priceMax));
        } else if (priceMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(pricePath, priceMin));
        } else {
            predicates.add(cb.lessThanOrEqualTo(pricePath, priceMax));
        }
    }

    private void modelPredicate(List<Predicate> predicates, String model, CriteriaBuilder cb, Root<Phone> root) {
        if (model != null) {
            predicates.add(cb.like(
                    cb.lower(root.get("model").as(String.class)),
                    "%" + model.toLowerCase() + "%"));
        }
    }
}
