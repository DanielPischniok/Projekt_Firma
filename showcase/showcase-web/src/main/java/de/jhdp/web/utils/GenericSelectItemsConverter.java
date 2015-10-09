package de.jhdp.web.utils;

import java.util.Collection;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

@FacesConverter("genericSelectItemsConverter")
public class GenericSelectItemsConverter implements Converter {

	Logger log = Logger.getLogger(GenericSelectItemsConverter.class.getName());
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String s) {
		UISelectItems items = getUISelectItems(uic);
		if (items == null) {
//			log.debug("%1$s: no UISelectItems found, returning null...", uic.getClientId());
			return null;
		}
		int i;
		try {
			i = Integer.valueOf(s);
		}
		catch (Exception e) {
			// not parsable-String value -> converting to null
			return null;
		}
		Object itemsValue = items.getValue();
		if (itemsValue instanceof Collection) {
			Object item = ((Collection<?>) itemsValue).toArray()[i];
			if (item instanceof SelectItem) {
				return ((SelectItem) item).getValue();
			}
			else {
				return item;
			}
		}
//		log.tracef("%1$s: could not convert value '%2$s', returning null", uic.getClientId(), s);
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		UISelectItems items = getUISelectItems(uic);
		if (items == null) {
//			log.debugf("%1$s: no UISelectItems found, returning null...", uic.getClientId());
			return null;
		}
		int i = 0;
		Object itemsValue = items.getValue();
		Collection<?> c = null;

		if (itemsValue instanceof Collection) {
			c = (Collection<?>) itemsValue;
		}
		if (c != null) {
			for (Object object : c) {
				Object itemValue;
				if (object instanceof SelectItem) {
					SelectItem si = (SelectItem) object;
					itemValue = si.getValue();
				}
				else {
					itemValue = object;
				}
				if (itemValue != null && itemValue.equals(o)) {
					return String.valueOf(i);
				}
				else {
					i++;
				}
			}
		}
//		log.tracef("%1$s: could not convert object '%2$s', returning null", uic.getClientId(), o);
		return null;
	}

	private UISelectItems getUISelectItems(UIComponent uic) {
		for (UIComponent comp : uic.getChildren()) {
			if (comp instanceof UISelectItems) {
				return (UISelectItems) comp;
			}
		}
		return null;
	}

}
